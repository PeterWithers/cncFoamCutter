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
})

var sendInProgress = false;
function sendGcode() {
    if (!sendInProgress) {
        sendInProgress = true;
        var portSelect = document.getElementById("portlist");
        var portName = portSelect.options[portSelect.selectedIndex].value;
        var port = new serialport(portName, function (err) {
            if (err) {
                document.getElementById("porterror").textContent = err.message;
                sendInProgress = false;
                return;
            }
        });
        var gcodeLines = document.getElementById("gcodeArea").value.split("\n");
        var gcodeTimerCallback = function () {
            var lineToSend = gcodeLines.shift();
            if (gcodeLines.length > 0) {
                port.write(lineToSend + "\n", function (err) {
                    if (err) {
                        document.getElementById('porterror').textContent = err.message;
                        sendInProgress = false;
                        return;
                    }
                    document.getElementById('porterror').textContent = "";
                    document.getElementById("gcodeArea").value = gcodeLines.join("\n");
                    setTimeout(gcodeTimerCallback, 100);
                });
            } else {
                sendInProgress = false;
            }
        }
        setTimeout(gcodeTimerCallback, 100);
    }
}