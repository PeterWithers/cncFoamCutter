/*
 * Copyright (C) 2018 Peter Withers
 */

/*
 * @since Jan 15, 2018 21:22 PM (creation date)
 * @author <peter-gthb@bambooradical.com>
 */

const serialport = require('serialport')

serialport.list((err, ports) => {
    if (err) {
        document.getElementById('porterror').textContent = err.message;
        return;
    } else {
        document.getElementById('porterror').textContent = "";
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
var sendInProgress = false;
var cancelRequest = false;
function sendGcode() {
    cancelRequest = false;
    if (!sendInProgress) {
        sendInProgress = true;
        var portSelect = document.getElementById("portlist");
        var portName = portSelect.options[portSelect.selectedIndex].value;
        var port = new serialport(portName, function (err) {
            if (err) {
                document.getElementById("porterror").textContent = err.message;
                sendInProgress = false;
                cancelRequest = false;
                return;
            }
        });
        var gcodeLines = document.getElementById("gcodeArea").value.split("\n");
        var gcodeTimerCallback = function () {
            if (cancelRequest) {
                sendInProgress = false;
                cancelRequest = false;
            } else {
                var lineToSend = gcodeLines.shift();
                if (gcodeLines.length > 0) {
                    port.write(lineToSend + "\n", function (err) {
                        if (err) {
                            document.getElementById('porterror').textContent = err.message;
                            sendInProgress = false;
                            cancelRequest = false;
                            return;
                        }
                        document.getElementById('porterror').textContent = "";
                        document.getElementById("gcodeArea").value = gcodeLines.join("\n");
                        setTimeout(gcodeTimerCallback, 100);
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