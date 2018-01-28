/*
 * Copyright (C) 2018 Peter Withers
 */

/*
 * @since Jan 15, 2018 21:22 PM (creation date)
 * @author <peter-gthb@bambooradical.com>
 */

require('require-rebuild')();
const SerialPort = require('serialport')
//const SerialPort = require('serialport/test');
//const MockBinding = SerialPort.Binding;
//MockBinding.createPort('/dev/MockSerialPort', {echo: true, record: true});
var port;
SerialPort.list((err, ports) => {
    if (err) {
        document.getElementById('porterror').textContent = err.message;
        return;
    } else {
        document.getElementById('porterror').textContent = "Found " + ports.length + " serial ports";
    }

    if (ports.length === 0) {
        document.getElementById('porterror').textContent = "No serial ports discovered";
    }

    var portSelect = document.getElementById("portlist");
    ports.forEach(port => {
        var option = document.createElement("option");
        option.text = port.comName;
        portSelect.add(option);
    });
    var option1 = document.createElement("option");
    option1.text = "<mock serial>";
    portSelect.add(option1);

    document.getElementById("portlist").addEventListener("change", function () {
        mockSerial = false;
        document.getElementById("portlist").disabled = true;
        if (port && port.isOpen) {
            port.close();
        }
        var portSelect = document.getElementById("portlist");
        var portName = portSelect.options[portSelect.selectedIndex].value;
        if ("<serial port off>" === portName) {
            document.getElementById("portlist").disabled = false;
            document.getElementById("porterror").textContent = "disconnected";
        } else if ("<mock serial>" === portName) {
            document.getElementById("portlist").disabled = false;
            mockSerial = true;
            document.getElementById("porterror").textContent = "mock serial";
        } else {
            document.getElementById("porterror").textContent = "connecting: " + portName;
            cancelRequest = true;
            port = new SerialPort(portName, {
                baudRate: 115200,
                disconnectedCallback: function () {
                    document.getElementById("porterror").textContent = "disconnected";
                    document.getElementById("portlist").disabled = false;
                }
            }, function (err) {
                if (err) {
                    document.getElementById("porterror").textContent = err.message;
                    document.getElementById("portlist").disabled = false;
                    return;
                } else {
                    document.getElementById("porterror").textContent = "ready";
                    document.getElementById("portlist").disabled = false;
                    return;
                }
            });
            port.on('data', portDataReceived);
        }
    });

    document.getElementById("sendButton").onclick = sendGcode;
    document.getElementById("cancelButton").onclick = function () {
        cancelRequest = true;
    };
    document.getElementById("leftButton").onclick = function () {
        jogRequest(-10, 0);
    };
    document.getElementById("rightButton").onclick = function () {
        jogRequest(10, 0);
    };
    document.getElementById("upButton").onclick = function () {
        jogRequest(0, 10);
    };
    document.getElementById("downButton").onclick = function () {
        jogRequest(0, -10);
    };
    document.getElementById("homeButton").onclick = function () {
        document.getElementById("gcodeArea").value = "M05 S0 G90 G0 X0 Y0 Z0";
        sendGcode();
    };
    document.getElementById("zeroButton").onclick = function () {
        document.getElementById("gcodeArea").value = "M05 S0 G92 X0 Y0 Z0";
        sendGcode();
    };
});

function jogRequest(xDist, yDist) {
    document.getElementById("gcodeArea").value =
            "M05 S0\n" // turn off laser
            + "G91\n" // Set to incremental Positioning
            + "G21\n" // Millimeters
            + "G0 X" + xDist + " Y" + yDist + "\n" // linear move
    sendGcode();
}

function portDataReceived(data) {
    document.getElementById("serialData").value += data;
    const charO = 111;
    const charK = 107;
    const charE = 101;
    const charR = 114;
    for (var bufferIndex = 0; bufferIndex < data.length - 1; bufferIndex++) {
        if (data[bufferIndex] === charE && data[bufferIndex + 1] === charR) {
            document.getElementById('gcodeerror').textContent = data;
        } else if (data[bufferIndex] === charO && data[bufferIndex + 1] === charK) {
            if (sentGcode.length > 0) {
                ackCount++;
                var lineToSend = sentGcode.shift();
                messagePreview(lineToSend);
//                if (lineToSend.startsWith("G1") || !lineToSend.startsWith("G0")) {
//                    break; // require a fresh OK for G0 or G1 codes
//                }
            }
        }
    }
    updateProgressIndicator();
}

window.addEventListener("message", function (event) {
    cancelRequest = true;
    document.getElementById("gcodeArea").value = event.data;
    totalCount = 0;
    sentGcode = [];
    ackCount = 0;
    updateProgressIndicator();
}, false);

function messagePreview(gcodeString) {
    document.getElementById("remoteFrame").contentWindow.postMessage(gcodeString, "*");
}

function updateProgressIndicator() {
    if (totalCount <= 1) {
        document.getElementById('completeIndicator').style.width = "0px";
        document.getElementById('progressIndicator').style.width = "0px";
        document.getElementById('progressIndicator').style.marginLeft = "0px";
    } else {
        var percentDone = ackCount / totalCount * 100;
        var percentProcessing = sentGcode.length / totalCount * 100;
        document.getElementById('completeIndicator').style.width = percentDone + "%";
        document.getElementById('progressIndicator').style.width = percentProcessing + "%";
        document.getElementById('progressIndicator').style.marginLeft = percentDone + "%";
    }
}

var writeAhead = 1;
var sendInProgress = false;
var cancelRequest = false;
var mockSerial = false;
var totalCount = 0;
var sentGcode = [];
var ackCount;
function sendGcode() {
    cancelRequest = false;
    if (!sendInProgress) {
        sendInProgress = true;
        sentGcode = [];
        ackCount = 0;
        writeAhead = document.getElementById("writeAhead").value;
        var gcodeLines = document.getElementById("gcodeArea").value.split("\n");
        totalCount = gcodeLines.length;
        var gcodeTimerCallback = function () {
            var portActive = (mockSerial) ? true : (port && port.isOpen);
            if (!portActive || cancelRequest) {
                sendInProgress = false;
                cancelRequest = false;
            } else if (sentGcode.length > writeAhead) {
                setTimeout(gcodeTimerCallback, 10);
            } else {
                if (gcodeLines.length > 0) {
                    var lineToSend = gcodeLines.shift();
                    sentGcode.push(lineToSend);
                    if (mockSerial) {
                        document.getElementById('porterror').textContent = "mock serial";
                        document.getElementById("gcodeArea").value = gcodeLines.join("\n");
                        setTimeout(gcodeTimerCallback, 10);
                        setTimeout(portDataReceived, 1000, Buffer.from('bla\nok\ns'));
                    } else {
                        port.write(lineToSend + "\n", function (err) {
                            if (err) {
                                document.getElementById('porterror').textContent = err.message;
                                sendInProgress = false;
                                cancelRequest = false;
                                return;
                            }
                            port.drain(function () {
                                document.getElementById('porterror').textContent = "";
                                document.getElementById("gcodeArea").value = gcodeLines.join("\n");
                                gcodeTimerCallback();
                            });
                        });
                    }
                } else {
                    sendInProgress = false;
                    cancelRequest = false;
                }
            }
            updateProgressIndicator();
        }
        setTimeout(gcodeTimerCallback, 100);
    }
}
