xRailDiameter = 8;
xRailLength = 350;
xRailSeparation = 700;

yRailDiameter = 16;
yRailLength = 110;

zRailDiameter = 20;
zRailLength = 900;

endBlockLength = 450;
endBlockWidth = 50;
endBlockHeight = 20;



module exampleFoamSize() {
	%cube([600, 1200, 60], center = true);
}

module stepperMotor() {
	stepperWidth = 42;
	stepperLength = 40;
	stepperShoulder = 2;
	stepperShoulderDiameter = 22;
	stepperShaft = 5;
	stepperShaftLenght = 64;
	stepperBoltDist = 42;
	stepperBolt = 3;
	stepperBoltLength = 10;
	cube([stepperWidth, stepperWidth, stepperLength], center = true);
	translate([0,0,stepperShaftLenght-stepperLength]) {
		cylinder(r = stepperShaft/2, h = stepperLength, center = true);
	}
	translate([0,0,stepperShoulder]) {
		cylinder(r = stepperShoulderDiameter/2, h = stepperShoulder+stepperLength, center = true);
	}
	for (rotation = [0, 90, 180, 270]) {
		rotate(rotation+45, [0,0,1]) translate([0,stepperBoltDist/2,stepperBoltLength]) {
			cylinder(r = stepperBolt/2.0, h = stepperLength, center = true);
		}
	}
}

module linearBearing() {
	blockWidth = 35;
	blockHeight = 21.5;
	blockLength = 30;
	rodHoleSize = 8+11;
	rodHoleOffset = 11;
	blockBoltDist = 42;
	blockBolt = 2;
	stepperBoltLength = 10;
	shoulderHeight = blockHeight-rodHoleSize/2;
	//%translate([0,rodHoleSize/4,0]) cube([blockWidth, blockHeight, blockLength], center = true);
	//hull() {
		cube([blockWidth, shoulderHeight, blockLength], center = true);
		translate([0,shoulderHeight/2,0]) {
			cylinder(r = rodHoleSize/2, h = blockLength, center = true);
		}
	//}
	//for (rotation = [0, 90, 180, 270]) {
	//	rotate(rotation+45, [0,0,1]) translate([0,blockBoltDist/2,stepperBoltLength]) {
	//		cylinder(r = blockBolt/2.0, h = blockLength, center = true);
	//	}
	//}
}

module xRodMount() {
    difference() {
    union() {
	difference() {
	union() {
	hull() {
		cube([5,15,75], center = true);
		translate([0,45/2-15/2,-75/2]) rotate(90, [1,0,0]) cube([5,15,45], center = true);
	}
	translate([0,45/2-15/2,-75/2-7]) rotate(90, [1,0,0]) cube([25,5,45], center = true);
	}
	union(){
	for (spacing = [30, -30]) 
		translate([0,-3,spacing]) cube([10,10,10], center = true);
	}
	}
            for (spacing = [30, -30]) 
		translate([0,-2.5,spacing]) {
			rotate(90, [1,0,0])
			difference() {
			cylinder(r = xRailDiameter/2+5, h = 10, center = true);
			translate([0,0,5]) cylinder(r = xRailDiameter/2+1, h = 10, center = true);
			}
            }

            rotate(90, [0,1,0]) translate([8,-4,7]) {
                intersection () {
                    union() {
                    translate([0,0,-6]) cylinder(r = 6, h = 5, center = true);
                    cylinder(r = 4, h = 20, center = true);
                    }
                    translate([0,4,-1.5]) cube([15,15,10], center = true);
		}
		%ballBearing(0, 0, 0);
            }
        }
        rotate(90, [0,1,0]) translate([8,-4,7]) cylinder(r = 1.5, h = 30, center = true);
		rotate(90, [0,0,1]) translate([6,8,-50]) cylinder(r = 2, h = 30, center = true);
		rotate(90, [0,0,1]) translate([6,-8,-50]) cylinder(r = 2, h = 30, center = true);
		rotate(90, [0,0,1]) translate([30,8,-50]) cylinder(r = 2, h = 30, center = true);
		rotate(90, [0,0,1]) translate([30,-8,-50]) cylinder(r = 2, h = 30, center = true);
    }
}

module xRailEndPlate() {
	translate([0,-xRailLength/2,0]) rotate(180, [0,0,1]) xRodMount();
	translate([0,xRailLength/2,0]) xRodMount();
	translate([50,xRailLength/2+30,0]) rotate(90, [0,-1,0]) stepperMotor();
}
module yRailEndPlate() {
	translate([10,300,62]) {
			cube([50,50,10], center = true);
	}
	translate([10,300,-62]) {
			cube([50,50,10], center = true);
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

module solidRod(railDiameter, railLength) {
	cylinder(r = railDiameter / 2, h = railLength, center = true);
}

module xRail() {
    translate([xRailSeparation/2,0,0]) {
            rotate(90, [1,0,0]) {
                    translate([0,-30,0]) {
                            solidRod(xRailDiameter, xRailLength);
                    }
                    translate([0,30,0]) {
                            solidRod(xRailDiameter, xRailLength);
                    }
            }
            xRailEndPlate();
            yRail();
    }
}

module wire() {
    rotate(90, [1,0,0])
        translate([0,0,-xRailSeparation/4])
            cylinder(r = 0.5, h = xRailSeparation/2, center = true);
}

module yRailMount() {
	// mount holes
	bearingBlockHoleHorSpacing = 18;
	bearingBlockHoleVerSpacing = 24;
	bearingBlockHoleDiameter = 5;
		
	ySlideMountHoleHorSpacing = 7;
	ySlideMountHoleVerSpacing = 48;
	ySlideMountHoleDiameter = 3;

	bearingBlockSpacing = 60;
	boreLength = 100;

	backPlateThickeness = 5;
	backPlateLength = bearingBlockSpacing+35;
	backPlateWidth = 30;

	endstopMountHeight = 24;
	endstopMountWidth = 16;
	endstopMountLength = 27;
	endstopHoleSpacing = 19;
	endstopHoleDiameter = 3;
    
	difference() {
		union() {
		// back plate
		cube([backPlateWidth,backPlateThickeness,backPlateLength], center = true);
		// endstop mount
			translate([-8,endstopMountHeight/2-backPlateThickeness/2,backPlateLength/2-1+endstopMountWidth/2]) {
		intersection() {
			difference() {
			union() {
				translate([0,0,-2.5]) cube([endstopMountLength,endstopMountHeight,endstopMountWidth-5], center = true);
				translate([-backPlateWidth/2+8.5,0,-10])cube([1,endstopMountHeight,endstopMountWidth], center = true);
			}
			union() {
				rotate(90, [1,0,0])	{
					// endstop mount holes
					for (spacing = [endstopHoleSpacing/2, -endstopHoleSpacing/2]) 
					translate([spacing-0.5,-4,-endstopMountHeight/2])
		        	    		cylinder(r = endstopHoleDiameter/2, h = 20, center = true);
				}
					}
				}
			translate([0,0,-6]) rotate(30, [1,0,0]) cube([endstopMountLength,endstopMountHeight*2,endstopMountWidth-3], center = true);
			}
		}
		}
	union() {
	rotate(90, [1,0,0])	{
		// linear bearing mount holes
		for (spacing = [bearingBlockSpacing/2, -bearingBlockSpacing/2]) 
		for (holeVerSpacing = [bearingBlockHoleVerSpacing/2, -bearingBlockHoleVerSpacing/2])
		for (holeHorSpacing = [bearingBlockHoleHorSpacing/2, -bearingBlockHoleHorSpacing/2])	{
        translate([holeHorSpacing,spacing+holeVerSpacing,0])
            cylinder(r = bearingBlockHoleDiameter/2, h = boreLength, center = true);
		}
		// slide mount holes
		for (holeVerSpacing = [ySlideMountHoleVerSpacing/2, -ySlideMountHoleVerSpacing/2])
		for (holeHorSpacing = [ySlideMountHoleHorSpacing/2, -ySlideMountHoleHorSpacing/2])	{
        translate([holeHorSpacing,holeVerSpacing,0])
            cylinder(r = ySlideMountHoleDiameter/2, h = boreLength, center = true);
				}
			}
		}
		// timing belt attachment holes
			for (spacing = [backPlateWidth/2-5, -backPlateWidth/2+5]) 
				translate([spacing,0,0])
					cube([3,10,8], center = true);
	}
}

module yRailSlide() {
    cube([15,15,90], center = true);
    translate([0,5,0]) cube([19,25,5], center = true);
}

module yRail() {
	rotate(90, [0,0,1]) {
            translate([100,25,0]) {
                translate([0,-7,0]) yRailMount();
                translate([0,4,0]) yRailSlide();
                for (offset = [-30, 30]) {
                    translate([0,-20,offset]) rotate(90, [0,1,0]) rotate(180, [1,0,0]){
                    linearBearing();
                    }
                }
                wire();
            }
	}
	//yRailEndPlate();
}

module zRail() {
	rotate(90, [0,1,0]) {
		translate([60,200,0]) {
			hollowTube(zRailDiameter, zRailLength);
		}
	translate([60,-200,0]) {
			hollowTube(zRailDiameter, zRailLength);
		}
	}
}

module endBlocks() {
	translate([zRailLength/2-endBlockWidth/2,0,-76]) {
			cube([endBlockWidth, endBlockLength, endBlockHeight], center = true);
		}
	translate([-zRailLength/2+endBlockWidth/2,0,-76]) {
			cube([endBlockWidth, endBlockLength, endBlockHeight], center = true);
	}
}


module assembly() {
	xRail();
	mirror([1,0,0]) xRail();
	#zRail();
	#endBlocks();
}
//exampleFoamSize();
//assembly();

//stepperMotor();
//linearBearing();
//yRail();
//translate([-300,-150,0]) xRail();
rotate(180, [1,0,0]) xRodMount();
//rotate(90, [1,0,0]) yRailMount();