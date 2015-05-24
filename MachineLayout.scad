xRailDiameter = 10;
xRailLength = 700;

yRailDiameter = 10;
yRailLength = 110;

zRailDiameter = 10;
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

module xRail() {
	translate([-300,0,0]) {
		rotate(90, [1,0,0]) {
			translate([0,-45,0]) {
				cylinder(r = xRailDiameter / 2, h = xRailLength, center = true);
			}
			translate([0,45,0]) {
				cylinder(r = xRailDiameter / 2, h = xRailLength, center = true);
			}
		}
		xRailEndPlate();
		yRail();
	}
}

module yRail() {
	rotate(90, [0,0,1]) {
		translate([300,-20,0]) {
			cylinder(r = yRailDiameter / 2, h = yRailLength, center = true);
		}
	}
	yRailEndPlate();
}

module zRail() {
	rotate(90, [0,1,0]) {
		translate([60,350,0]) {
			cylinder(r = zRailDiameter / 2, h = zRailLength, center = true);
		}
	translate([60,-350,0]) {
			cylinder(r = zRailDiameter / 2, h = zRailLength, center = true);
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

