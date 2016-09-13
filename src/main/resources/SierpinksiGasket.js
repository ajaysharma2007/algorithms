/**
 * Created by ajay on 13/09/16.
 */
var drawShape = function(x, y, radius) {
    if(radius > 100) {

        // line(x+radius/2, y, x+radius/2, y+radius );
        // line(x, y+radius/2, x+radius, y+radius/2 );

        var new_radius = radius/2;
        drawShape(x, y, new_radius);
        drawShape(x+radius/2, y, new_radius);
        drawShape(x+radius/2, y+radius/2, new_radius);

    } else {
        fill(14, 10, 245);
        rect(x, y, radius, radius);
        // line(x, y, x+radius, y+radius);
        // line(x+radius, y, x, y+radius);
    }

};

var x = width/15;
var y = height/15;
var radius = 344;
fill(238, 255, 0);
rect(x, y, radius, radius);
drawShape(x, y, radius);


