xRailDiameter = 10;
xRailLength = 700;

yRailDiameter = 16;
yRailLength = 110;

zRailDiameter = 20;
zRailLength = 1300;

endBlockLength = 720;
endBlockWidth = 50;
endBlockHeight = 20;

module exampleFoamSize() {
	%cube([1200, 600, 60], center = true);
}

module xRailEndPlate() {
	translate([50,365,0]) {
			cube([150,10,150], center = true);
	}
	translate([50,-365,0]) {
			cube([150,10,150], center = true);
	}
}

module yRailEndPlate() {
	translate([10,300,62]) {
			cube([50,50,10], center = true);
	}
	translate([10,300,-62]) {
			cube([50,50,10], center = true);
	}
}

module endRailBearingMount(rodDiameter, length) {
	difference(){
		rotate(90, [0,1,0]) difference(){
				cylinder(r = rodDiameter*1.2, h = length, center = true);
				translate([-rodDiameter*0.6,0,0]) cylinder(r = rodDiameter/2+1, h = length+1, center = true);
		}
		translate([0,0,-rodDiameter-1]) cylinder(r = rodDiameter/2+1, h = length+1, center = true);
	}
}

module yRailEndRoller() {
	translate([0,0,70]) {
			#endRailBearingMount(yRailDiameter, 30);
			ballBearing(-30,yRailDiameter,0);
			ballBearing(30,-yRailDiameter,0);
	}
	translate([0,0,-70]) {
			ballBearing(30,yRailDiameter,-30);
			ballBearing(30,yRailDiameter,30);
			ballBearing(-30,-yRailDiameter,0);
	}
}

module ballBearing(rotation, distance, offset) {
	translate([offset,distance,0])
	rotate(rotation, [1,0,0])
	difference() {
    		cylinder(r = 11, h = 7, center = true);
	    cylinder(r = 4, h = 7 + 1, center = true);
	}
}

module hollowTube(railDiameter, railLength) {
	difference() {
    		cylinder(r = railDiameter / 2, h = railLength, center = true);
	    cylinder(r = railDiameter / 2 - 0.7, h = railLength + 1, center = true);
	}
}

module xRail() {
	translate([-300,0,0]) {
		rotate(90, [1,0,0]) {
			translate([0,-45,0]) {
				hollowTube(xRailDiameter, xRailLength);
			}
			translate([0,45,0]) {
				hollowTube(xRailDiameter, xRailLength);
			}
		}
		xRailEndPlate();
		yRail();
	}
}

module yRail() {
	rotate(90, [0,0,1]) {
		translate([300,-20,0]) {
			hollowTube(yRailDiameter, yRailLength);
			yRailEndRoller();
		}
	}
	//yRailEndPlate();
}

module zRail() {
	rotate(90, [0,1,0]) {
		translate([60,350,0]) {
			hollowTube(zRailDiameter, zRailLength);
		}
	translate([60,-350,0]) {
			hollowTube(zRailDiameter, zRailLength);
		}
	}
}

module endBlocks() {
	translate([650-endBlockWidth/2,0,-76]) {
			cube([endBlockWidth, endBlockLength, endBlockHeight], center = true);
		}
	translate([-650+endBlockWidth/2,0,-76]) {
			cube([endBlockWidth, endBlockLength, endBlockHeight], center = true);
	}
}


module assembly() {
	xRail();
	mirror([1,0,0]) xRail();
	zRail();
	endBlocks();
}
exampleFoamSize();
assembly();

