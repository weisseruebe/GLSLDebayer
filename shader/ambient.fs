uniform sampler2D jvr_Texture0;
varying vec2 texture_coordinate;

uniform vec4 jvr_Global_Ambient;

void main (void)
{
  gl_FragColor= 	texture2D(jvr_Texture0, texture_coordinate) * 1.0;
  gl_FragColor.a =  texture2D(jvr_Texture0, texture_coordinate).a;
}
