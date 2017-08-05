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

bearingBlockSpacing = 60;
bearingBlockHoleHorSpacing = 18;
bearingBlockHoleVerSpacing = 24;
bearingBlockHoleDiameter = 5;	
boreLength = 100;

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
		rotate(rotation+45, [0,0,1]) translate([0,stepperBoltDist/2+1,stepperBoltLength]) {
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

module xRodMountIdler() {
    difference() {
        union() {
            xRodMount();
            rotate(90, [0,1,0]) translate([5,10,6]) {
                /*
                intersection () {
                    union() {
                        translate([0,0,-2.5]) cylinder(r1 = 6, r2 = 2 , h = 2, center = true);
                        cylinder(r = 2.4, h = 20, center = true);
     	               translate([0,-6,0]) cube([0.5,15,7], center = true);
                    }
                    translate([0,0,-1.5]) cube([15,15,7], center = true);
                }
                */
                %ballBearingSmall(0, 0, 0);
            }
			translate([-5,0.5,7]) endStopPosts();
        }
        union() {
            hull() {
                rotate(90, [0,1,0]) translate([5,19,7]) cylinder(r = 2, h = 30, center = true);
                rotate(90, [0,1,0]) translate([5,9,7]) cylinder(r = 2, h = 30, center = true);
            }
            translate([-7,0.5,7]) endStop();
        }
    }
}

module xRodMountMotor() {
    difference() {
        union() {
            xRodMount();
            rotate(90, [0,1,0]) translate([5,25,7]) {
                cylinder(r = 4, h = 20, center = true);
                translate([0,0,-7]) hull() {
                    for (angle = [0, 90, 180, 270]) {
                        rotate(angle, [0,0,1]) rotate(45, [0,0,1]) 
                            translate([15,0,0]) 
                                cylinder(r = 12, h = 5, center = true);
                    }
                }
                translate([0,0,-30]) %stepperMotor();
            }
        }
        rotate(90, [0,1,0]) translate([5,25,7]) union() {
            // motor centre hole
            cylinder(r = 12, h = 30, center = true);
            for (angle = [0, 90, 180, 270]) {
                        // motor bolt holes
                        rotate(angle, [0,0,1]) rotate(45, [0,0,1]) translate([22,0,0]) cylinder(r = 2, h = 30, center = true);
            }
        }
    }
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
            hull() {
                translate([0,45/2-15/2-22,-75/2-7]) rotate(90, [1,0,0]) 
                {
                    cube([25,5,1], center = true);
                    translate([0,30,0]) cube([5,5,1], center = true);
                }
            }
            hull() {
                translate([0,45/2-15/2,-75/2-7]) rotate(90, [1,0,0]) cube([25,5,45], center = true);
                translate([0,45/2-15/2,-75/2]) rotate(90, [1,0,0]) cube([5,15,45], center = true);
            }   
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
			translate([0,0,3]) cylinder(d1 = xRailDiameter, d2=xRailDiameter+1, h = 5, center = true);
			}
            }
        }
        // base mount holes
	rotate(90, [0,0,1]) translate([6,8,-40]) cylinder(r = 2, h = 30, center = true);
	rotate(90, [0,0,1]) translate([6,-8,-40]) cylinder(r = 2, h = 30, center = true);
	rotate(90, [0,0,1]) translate([30,8,-40]) cylinder(r = 2, h = 30, center = true);
	rotate(90, [0,0,1]) translate([30,-8,-40]) cylinder(r = 2, h = 30, center = true);
        // base mount counter sunk holes
	rotate(90, [0,0,1]) translate([6,8,-33]) cylinder(r = 3.5, h = 10, center = true);
	rotate(90, [0,0,1]) translate([6,-8,-33]) cylinder(r = 3.5, h = 10, center = true);
	rotate(90, [0,0,1]) translate([30,8,-33]) cylinder(r = 3.5, h = 10, center = true);
	rotate(90, [0,0,1]) translate([30,-8,-33]) cylinder(r = 3.5, h = 10, center = true);
    }
}

module xRailEndPlate() {
	translate([0,-xRailLength/2,0]) rotate(180, [0,0,1]) xRodMountIdler();
	translate([0,xRailLength/2,0]) xRodMountMotor();
}
module yRailEndPlate() {
	translate([10,300,62]) {
			cube([50,50,10], center = true);
	}
	translate([10,300,-62]) {
			cube([50,50,10], center = true);
	}
}

module ballBearingSmall(rotation, distance, offset) {
	translate([offset,distance,0])
	rotate(rotation, [1,0,0])
	difference() {
    		cylinder(r = 5, h = 4, center = true);
	    cylinder(r = 2.4, h = 4 + 1, center = true);
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

module endStopPosts() {
    endstopHoleSpacing = 19;
    endstopPostDiameter = 6;
    endstopPostLength = 6;
    rotate(90, [0,1,0])
        for (spacing = [endstopHoleSpacing/2, -endstopHoleSpacing/2]) 
            translate([spacing-6.5,-5,0])
                cube([endstopPostDiameter-2, endstopPostDiameter, endstopPostLength], center = true);
}

module endStop() {
    endstopHoleSpacing = 19;
    endstopHoleDiameter = 2;
    %cube([9,16,39], center = true);
    rotate(90, [0,1,0])
        for (spacing = [endstopHoleSpacing/2, -endstopHoleSpacing/2]) 
            translate([spacing-6.5,-5,0])
                #cylinder(r = endstopHoleDiameter/2, h = 10, center = true);
}

module yRailSlideMount() {
	// mount holes
		
	ySlideMountHoleHorSpacing = 7;
	ySlideMountHoleVerSpacing = 48;
	ySlideMountHoleDiameter = 2;


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
                translate([0,1.5,3-backPlateLength/2]) cube([backPlateWidth/3,backPlateThickeness + 3, 6], center = true);
                // slide mount tabs
                for (slideBlockX = [6.7, -6.7]) {
                    for (slideBlockY = [37, -27]) 
                        translate([slideBlockX,0.5,slideBlockY]) 
                            cube([2,backPlateThickeness + 1, 7.5], center = true);
                }
		// endstop mount
                translate([-8,endstopMountHeight/2-backPlateThickeness/2,backPlateLength/2-1+endstopMountWidth/2]) {
                    /*intersection() {
			union() {
				translate([8,0,-3]) cube([endstopMountLength+3,endstopMountHeight,endstopMountWidth-8], center = true);
				translate([-backPlateWidth/2+8.5,0,-10])cube([1,endstopMountHeight,endstopMountWidth+5], center = true);
                                translate([backPlateWidth/2+7.5,0,-10])cube([1,endstopMountHeight,endstopMountWidth+5], center = true);
                        }
			union() {
                            translate([8,0,-8]) rotate(30, [1,0,0]) cube([endstopMountLength+3,endstopMountHeight*2,endstopMountWidth-3], center = true);
                        }
                    }*/
                    difference() {
                        union() {
                            // endstop mount posts
                            translate([15,endstopMountHeight/2+2.9,0]) rotate(90, [0,-1,0]) rotate(90, [0,0,-1]) endStopPosts();
                            difference() {
                                hull(){
                                    translate([backPlateWidth/2-6.5,14,-5])cube([23,4,1], center = true);
                                    translate([backPlateWidth/2-8,14,15])cube([10,5,1], center = true);
                                }
                                union(){
                                    translate([backPlateWidth/2-6.5,15,-5])cube([12,4,12], center = true);
                                    translate([backPlateWidth/2-6.5,14,-7])cube([15,6,10], center = true);
                                }
                            }
                        }
                        // endstop mount holes
                        translate([15,endstopMountHeight/2+4.5,0]) rotate(90, [0,-1,0]) rotate(90, [0,0,-1]) #endStop();
                    }
                }
            }
	union() {
	rotate(90, [1,0,0])	{
		// linear bearing bolt holes
		for (spacing = [bearingBlockSpacing/2, -bearingBlockSpacing/2]) 
		for (holeVerSpacing = [bearingBlockHoleVerSpacing/2, -bearingBlockHoleVerSpacing/2])
		for (holeHorSpacing = [bearingBlockHoleHorSpacing/2, -bearingBlockHoleHorSpacing/2])	{
                    translate([holeHorSpacing,spacing+holeVerSpacing,0])
                        cylinder(r = bearingBlockHoleDiameter/2, h = boreLength, center = true);
		}
                // linear bearing bolt inset holes
		for (holeVerSpacing = [bearingBlockHoleVerSpacing/2 + bearingBlockSpacing/2, -bearingBlockHoleVerSpacing/2 - bearingBlockSpacing/2])
		for (holeHorSpacing = [bearingBlockHoleHorSpacing/2, -bearingBlockHoleHorSpacing/2])	{
                    translate([holeHorSpacing,holeVerSpacing,-1.1])
                        cylinder(r = 4, h = 3, center = true);
		}
                // stepper cavity
                #translate([0,46+6,-12]) rotate(90, [1,0,0]) cylinder(d = 16, h = 12, center = true);
                #translate([0,46+6,-7.5]) rotate(90, [1,0,0]) cube([16,10,10], center = true);
                #translate([5,46+6,-12]) rotate(90, [1,0,0]) cube([10,10,10], center = true);
		// slide mount holes
		/*translate([0,2,0]) for (holeVerSpacing = [ySlideMountHoleVerSpacing/2, -ySlideMountHoleVerSpacing/2])
		for (holeHorSpacing = [ySlideMountHoleHorSpacing/2, -ySlideMountHoleHorSpacing/2])	{
        translate([holeHorSpacing,holeVerSpacing,0])
            cylinder(r = ySlideMountHoleDiameter/2, h = boreLength, center = true);
				}*/
			}
		}
		// timing belt attachment holes
                    translate([0,7.5-1.5,5]) cube([18,10,5], center = true);
                    for (spacing = [backPlateWidth/2-5, -backPlateWidth/2+5]) 
                        translate([spacing,0,5])
                                cube([3,10,8], center = true);
	}    
}

module yRailSlideMountMK2left() {
    yRailSlideMount();
    scale([1,1,1]) verticalRod();
}

module yRailSlideMountMK2right() {
    yRailSlideMount();
    scale([-1,1,1]) verticalRod();
    translate([0,0,-30]) yWireGuide();
}

module yRailSlideMountMK2lower() {
    difference() {
        union() {
            yRailSlideMount();
            scale([-1,1,1]) verticalRod();
        }
        translate([0,20,23])
            #cube([50,50,120], center = true);
    }
}

module yRailSlideMountMK2upper() {
    difference() {
        union() {
            yRailSlideMount();
            scale([-1,1,1]) verticalRod();
        }
        translate([0,20,-23])
            #cube([50,50,120], center = true);
    }
}

module verticalRod() {
    verticalRodLength = 150;
    verticalRodDiameter = 5;
    verticalRodOffset = 0;
    verticalRodInset = 36;
    verticalBearingDiameter = 10;
    verticalBearingLength = 15;
    backPlateHeight = 100;
    //translate([bearingBlockHoleHorSpacing,0,0]) {
        translate([verticalRodOffset,verticalRodInset,0]) {
            //#cylinder(r = verticalRodDiameter/2, h = verticalRodLength, center = true);
            //#cylinder(r = verticalBearingDiameter/2, h = verticalBearingLength, center = true);
            translate([0,2,0]) difference() {
                union() { 
// 80mm distance currently between mounts
// 85mm travel in the slide
// therefore: 80+15+5 
                    // make the rod mounts
                    translate([0,0,(-backPlateHeight/2+16.25)+80+15+7]) {
                        hull() {
                        // vertical rod housing
                        cylinder(r = (verticalBearingDiameter/1.8)-0.5, h = verticalBearingLength/2, center = true);
                        translate([0,-38,3.2])
                            cube([30,5,1], center = true);
                        }
                        for (spacing = [12, -12]) {
                            translate([spacing,-35,-9])
                                cube([1,7,25], center = true);
                        }
                    }
                    // make the rod mounts
                    translate([0,0,-backPlateHeight/2+6.25]) hull() {
                        // vertical rod housing
                        cylinder(r = (verticalBearingDiameter/1.8)-0.5, h = verticalBearingLength/2, center = true);
                        translate([0,-38,-3.2])
                            cube([30,5,1], center = true);
                    }

                    translate([0,-20,-backPlateHeight/2+6])
                        cube([10,35,5], center = true);
                    translate([0,-20,(-backPlateHeight/2+14)+80+15+10])
                        cube([10,35,5], center = true);
                }
                union(){
                    translate([0,0,(verticalRodLength-backPlateHeight)/2+4]) cylinder(r = verticalRodDiameter/2, h = verticalRodLength, center = true);
                    //translate([0,0,6]) cylinder(r = 5, h = 15+77, center = true);
                    #translate([0,0,6]) cylinder(r = 5, h = 15, center = true);
                    translate([0,-13.5,-41]) cylinder(r = 2, h = 5, center = true);
                }
            }
            translate([0,-36,backPlateHeight/2+9]) cube([30,5,25], center = true);
            //translate([-14.5,-35,backPlateHeight/2+5]) cube([1,7,17], center = true);
            //translate([12.5,-38,backPlateHeight/2+5]) cube([5,1,17], center = true);
            //translate([-12.5,-38,backPlateHeight/2+5]) cube([5,1,17], center = true);
            //translate([0,0,35]) yTensionGuide();
            //translate([-3,0,(-backPlateHeight/2+6.25)+80+15+5]) hull() {
              //  translate([-5,-2,0]) cube([3,5,7.5], center = true);
                //translate([-10,-2,-10]) cube([13,5,3], center = true);
            //}
        }
/*        rotate(90, [1,0,0])	{
            // linear bearing mount holes
            for (spacing = [bearingBlockSpacing/2, -bearingBlockSpacing/2]) 
            #for (holeVerSpacing = [bearingBlockHoleVerSpacing/2, -bearingBlockHoleVerSpacing/2])
            for (holeHorSpacing = [bearingBlockHoleHorSpacing*1.5])	{ //, -bearingBlockHoleHorSpacing/2
                translate([holeHorSpacing,spacing+holeVerSpacing,0])
                cylinder(r = bearingBlockHoleDiameter/2, h = boreLength, center = true);
            }
        } */
    //}
}
module yWireGuide(){
    linkageArmPosistionX = -0;
    linkageArmPosistionY = -3.5;
    linkageArmPosistionZ = 24.5;
    difference(){
        union(){
            //translate([0,38,0]) cylinder(r = 7.5, h = 1.5, center = true);
            hull(){
                //bearing cover
                translate([0,38,3]) cylinder(r = 6.2, h = 15, center = true);
                translate([-3,37,linkageArmPosistionY+2]) cylinder(r = 6, h = 6, center = true);
                translate([3,37,linkageArmPosistionY+2]) cylinder(r = 6, h = 6, center = true);
                #translate([11,37,linkageArmPosistionY+11]) cylinder(r = 1, h = 1, center = true);
                #translate([-11,37,linkageArmPosistionY+11]) cylinder(r = 1, h = 1, center = true);

                // wire tensioner outer
                //rotate(90, [0,1,0]) {
                //    translate([-7,12,5]) cylinder(r = 12, h = 8, center = true);
                //}
            }
            hull(){
                // linkage arm
                translate([-9,36,linkageArmPosistionY]) cylinder(r = 4, h = 2, center = true);
                translate([9,36,linkageArmPosistionY]) cylinder(r = 4, h = 2, center = true);
                translate([linkageArmPosistionX,linkageArmPosistionZ,linkageArmPosistionY]) cylinder(r = 4, h = 2, center = true);
            }
            //%translate([linkageArmPosistionX,2+22.5,0]) cylinder(r = 2, h = 30, center = true);
        }
        union(){
            // vertical slide connection hole
            translate([linkageArmPosistionX,linkageArmPosistionZ,0]) cylinder(r = 2, h = 30, center = true);
            // linear bearing cavity
            translate([0,38,3.5]) cylinder(r = 5.5, h = 15, center = true);
            // vertical rod cavity
            translate([0,38,0]) cylinder(r = 4.5, h = 35, center = true);
            // the vertical rod cavity and the end cap cavity create a shoulder 1mm high that prevents the bearing sliding out the top of the guide
            // end cap cavity
            translate([0,38,19]) cylinder(r = 5.5, h = 17, center = true);
            // wire tensioner cavity
            // wire cavity
            hull(){
                translate([0,38,3]) cylinder(r = 5.2, h = 6, center = true);
                translate([0,38,0]) cylinder(r = 7.2, h = 3, center = true);
            }
            // wire entrance
            //translate([0,43,0]) cube([30,15,2], center = true);            
        }
    }
}

module yTensionGuide(){
    linkageArmPosistion = -4;
    difference(){
        union(){
            hull(){
                //bearing cover
                translate([0,2,3]) cylinder(r = 6.2, h = 15, center = true);
                translate([0,20,linkageArmPosistion]) cylinder(r = 4, h = 2, center = true);

                // wire tensioner outer
                rotate(90, [0,1,0]) {
                    translate([-7,12,5]) cylinder(r = 12, h = 8, center = true);
                }
            }
            hull(){
                // linkage arm
                translate([0,20,linkageArmPosistion]) cylinder(r = 4, h = 2, center = true);
                translate([-21,21.5,linkageArmPosistion]) cylinder(r = 4, h = 2, center = true);
            }
        }
        union(){
            // vertical slide connection hole
            translate([-21,21.5,0]) cylinder(r = 2, h = 30, center = true);
            // linear bearing cavity
            translate([0,2,2]) cylinder(r = 5.5, h = 15, center = true);
            // vertical rod cavity
            translate([0,2,0]) cylinder(r = 4.5, h = 35, center = true);
            // the vertical rod cavity and the end cap cavity create a shoulder 1mm high that prevents the bearing sliding out the top of the guide
            // end cap cavity
            translate([0,2,19]) cylinder(r = 5.5, h = 17, center = true);
            // wire tensioner cavity
            rotate(90, [0,1,0]) {
                // spring cavity
                translate([-7,12,10]) cylinder(r = 7, h = 10, center = true);
                // spool cavity
                translate([-7,12,6]) cylinder(r = 5, h = 14, center = true);
                // axle space
                translate([-7,12,6]) cylinder(r = 1, h = 25, center = true);
                // wire entrance
                #translate([-5,13,1]) cube([5,25,3], center = true);
            }
        }
    }
}
module yRailSlide() {
    cube([15,15,90], center = true);
    translate([0,5,0]) cube([19,25,5], center = true);
}

module yRail() {
	rotate(90, [0,0,1]) {
            translate([100,25,0]) {
                translate([0,-7,0]) yRailSlideMount();
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

target = "xRodMountIdler";
if (target == "xRodMountIdler") {
    rotate(90, [1,0,0]) xRodMountIdler();
} else if (target == "xRodMountMotor") {
    rotate(90, [1,0,0]) xRodMountMotor();
} else if (target == "yRailSlideMount") {
    rotate(90, [1,0,0]) yRailSlideMount();
} else if (target == "yRailSlideMountMK2left") {
    rotate(90, [1,0,0]) yRailSlideMountMK2left();
} else if (target == "yRailSlideMountMK2right") {
    rotate(90, [1,0,0]) yRailSlideMountMK2right();
} else if (target == "yRailSlideMountMK2upper") {
    rotate(90, [1,0,0]) yRailSlideMountMK2upper();
} else if (target == "yRailSlideMountMK2lower") {
    rotate(90, [1,0,0]) yRailSlideMountMK2lower();
} else if (target == "yTensionGuide") {
    rotate(90, [0,0,1]) scale([-1,1,1]) yTensionGuide();
} else if (target == "yWireGuide") {
    rotate(90, [0,0,1]) scale([-1,1,1]) yWireGuide();
}