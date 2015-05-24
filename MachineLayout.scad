xRailDiameter = 10;
xRailLength = 700;

yRailDiameter = 10;
yRailLength = 110;

zRailDiameter = 10;
zRailLength = 1300;

module exampleFoamSize() {
	%cube([1200, 600, 60], center = true);
}

module xRail() {
	rotate(90, [1,0,0]) {
		cylinder(r = xRailDiameter / 2, h = xRailLength, center = true);
	}
}

module yRail() {
	rotate(90, [0,0,1]) {
		cylinder(r = yRailDiameter / 2, h = yRailLength, center = true);
	}
}

module zRail() {
	rotate(90, [0,1,0]) {
		cylinder(r = zRailDiameter / 2, h = zRailLength, center = true);
	}
}

module assembly() {
	xRail();
	yRail();
	zRail();
}

exampleFoamSize();
assembly();

