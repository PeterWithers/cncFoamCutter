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
	stepperWidth = 50;
	stepperLength = 60;
	stepperShaft = 5;
	stepperShaftLenght = 30;
	stepperBoltDist = 42;
	stepperBolt = 4;
	stepperBoltLength = 10;
	cube([stepperWidth, stepperWidth, stepperLength], center = true);
	translate([0,0,stepperShaftLenght]) {
		cylinder(r = stepperShaft/2, h = stepperLength, center = true);
	}
	for (rotation = [0, 90, 180, 270]) {
		rotate(rotation+45, [0,0,1]) translate([0,stepperBoltDist/2,stepperBoltLength]) {
			cylinder(r = stepperBolt/2.0, h = stepperLength, center = true);
		}
	}
}

module linearBearing() {
	blockWidth = 20;
	blockLength = 30;
	rodHoleSize = 8;
	rodHoleOffset = 5;
	blockBoltDist = 42;
	blockBolt = 2;
	stepperBoltLength = 10;
	cube([blockWidth, blockWidth, blockLength], center = true);
	translate([0,rodHoleOffset,0]) {
		cylinder(r = rodHoleSize/2, h = blockLength*2, center = true);
	}
	//for (rotation = [0, 90, 180, 270]) {
	//	rotate(rotation+45, [0,0,1]) translate([0,blockBoltDist/2,stepperBoltLength]) {
	//		cylinder(r = blockBolt/2.0, h = blockLength, center = true);
	//	}
	//}
}


module xRailEndPlate() {
	translate([0,xRailLength/2,0]) {
			cube([50,5,150], center = true);
	}
	translate([0,-xRailLength/2,0]) {
			cube([50,5,150], center = true);
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
                    translate([0,-50,0]) {
                            solidRod(xRailDiameter, xRailLength);
                    }
                    translate([0,50,0]) {
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
    
	difference() {
		cube([30,5,bearingBlockSpacing+35], center = true);
	union() {
	rotate(90, [1,0,0])	{
		for (spacing = [bearingBlockSpacing/2, -bearingBlockSpacing/2]) 
		for (holeVerSpacing = [bearingBlockHoleVerSpacing/2, -bearingBlockHoleVerSpacing/2])
		for (holeHorSpacing = [bearingBlockHoleHorSpacing/2, -bearingBlockHoleHorSpacing/2])	{
        translate([holeHorSpacing,spacing+holeVerSpacing,0])
            cylinder(r = bearingBlockHoleDiameter/2, h = boreLength, center = true);
	}

		for (holeVerSpacing = [ySlideMountHoleVerSpacing/2, -ySlideMountHoleVerSpacing/2])
		for (holeHorSpacing = [ySlideMountHoleHorSpacing/2, -ySlideMountHoleHorSpacing/2])	{
        translate([holeHorSpacing,holeVerSpacing,0])
            cylinder(r = ySlideMountHoleDiameter/2, h = boreLength, center = true);
				}
			}
		}
	}
}

module yRail() {
	rotate(90, [0,0,1]) {
            translate([100,20,0]) {
                yRailMount();
                cube([15,15,100], center = true);
                translate([0,5,0]) cube([19,25,5], center = true);
                for (offset = [-50, 50]) {
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
yRailMount();