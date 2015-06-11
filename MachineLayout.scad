xRailDiameter = 16;
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

module endRailBearingMount(rodDiameter, length, sideA, sideB) {
	difference(){
		rotate(90, [0,1,0]) difference(){
				cylinder(r = rodDiameter*1.2, h = length, center = true);
//			union() {
				translate([-rodDiameter*0.6,0,0]) cylinder(r = rodDiameter/2+1, h = length+1, center = true);
//				translate([offset,distance,0])
//	rotate(90, [1,0,1]) rotate(30, [1,0,0]) 
  //  		translate([0,0,-18]) cylinder(r = 120, h = 19, center = true);
			}
//		}
		union() {
			translate([0,0,-rodDiameter-1]) cylinder(r = rodDiameter/2+1, h = 30+1, center = true);
			for(bearingOffset = sideA) {
				translate([bearingOffset,0,0]) ballBearingSpace(-30,yRailDiameter,0);
			}
			for(bearingOffset = sideB) {
				translate([bearingOffset,0,0]) 
				ballBearingSpace(30,-yRailDiameter,0);
			}
		}
	}
}

module yRailEndRoller() {
	translate([0,0,70]) {
			endRailBearingMount(yRailDiameter, 30, [0],[0]);
			ballBearing(-30,yRailDiameter,0);
			ballBearing(30,-yRailDiameter,0);
	}
	translate([0,0,-70]) {
			rotate(180, [0,1,0]) endRailBearingMount(yRailDiameter, 50, [15,-15],[0]);
			ballBearing(30,yRailDiameter,-15);
			ballBearing(30,yRailDiameter,15);
			ballBearing(-30,-yRailDiameter,0);
	}
}

module ballBearingSpace(rotation, distance, offset) {
	translate([offset,distance,0])
	rotate(rotation, [1,0,0]) {
		difference() {
    		union() {
                    cylinder(r = 12, h = 9, center = true);
                    translate([0,0,9]) cylinder(r = 120, h = 19, center = true);
                }
		union() {
			translate([0,0,-6.3])	cylinder(r = 6, h = 5, center = true);
			translate([0,0,-3])	cylinder(r1 = 4,r2 = 3, h = 5, center = true);
			}
		}
		translate([0,0,10])	cylinder(r = 6, h = 7 + 10, center = true);
  	  	cylinder(r = 1, h = 70, center = true);
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
			translate([0,115,0]) {
				hollowTube(xRailDiameter, xRailLength);
			}
		}
		xRailEndPlate();
		yRail();
	}
}

module yRail() {
	rotate(90, [0,0,1]) {
		translate([300,0,35]) {
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

// export the single rod end
//translate([50,0,0])rotate(90, [0,1,0]) endRailBearingMount(yRailDiameter, 50, [15, -15], [0]);

// export the single rod end
//rotate(90, [0,1,0]) endRailBearingMount(yRailDiameter, 30,[0],[0]);

