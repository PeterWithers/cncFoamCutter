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

    document.getElementById("portlist").addEventListener("change", function () {
        document.getElementById("portlist").disabled = true;
        if (port && port.isOpen) {
            port.close();
        }
        var portSelect = document.getElementById("portlist");
        var portName = portSelect.options[portSelect.selectedIndex].value;
        if ("<serial port off>" === portName) {
            document.getElementById("portlist").disabled = false;
            document.getElementById("porterror").textContent = "disconnected";
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
            port.on('data', function (data) {
                document.getElementById("serialData").value += data;
            });
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
        jogRequest(0, -10);
    };
    document.getElementById("downButton").onclick = function () {
        jogRequest(0, 10);
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

function messagePreview(gcodeString) {
    document.getElementById("remoteFrame").contentWindow.postMessage(gcodeString, "*");
}

var sendInProgress = false;
var cancelRequest = false;
function sendGcode() {
    cancelRequest = false;
    if (!sendInProgress) {
        sendInProgress = true;
        var gcodeLines = document.getElementById("gcodeArea").value.split("\n");
        var gcodeTimerCallback = function () {
            if (!port || !port.isOpen || cancelRequest) {
                sendInProgress = false;
                cancelRequest = false;
            } else {
                if (gcodeLines.length > 0) {
                    var lineToSend = gcodeLines.shift();
                    messagePreview(lineToSend);
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
                } else {
                    sendInProgress = false;
                    cancelRequest = false;
                }
            }
        }
        setTimeout(gcodeTimerCallback, 100);
    }
}
