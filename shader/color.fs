uniform vec3 color;
uniform sampler2D texture0;
uniform float test;
varying vec2 texture_coordinate;

void main (void)
{
	//gl_FragColor =  texture2D(texture0,texture_coordinate);//vec4(1,0,0,1);
	gl_FragColor =  vec4(test,0,0,1);
}
