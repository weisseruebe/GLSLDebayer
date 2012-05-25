attribute vec4 jvr_Vertex;
attribute vec3 jvr_Normal;
attribute vec2 jvr_TexCoord;

uniform vec4 jvr_LightSource_Position;
uniform mat3 jvr_NormalMatrix;
uniform mat4 jvr_ModelViewMatrix;
uniform mat4 jvr_ModelViewProjectionMatrix;

varying vec3 normalV;
varying vec3 lightDirV;
varying vec3 eyeDirV;
varying vec2 texture_coordinate;

void main(void)
{
  vec3 vertexV = (jvr_ModelViewMatrix * jvr_Vertex).xyz;
  eyeDirV = -vertexV;
  lightDirV = jvr_LightSource_Position.xyz - vertexV;
  normalV = normalize(jvr_NormalMatrix * jvr_Normal);
  texture_coordinate = jvr_TexCoord;
  gl_Position = jvr_ModelViewProjectionMatrix * jvr_Vertex;
}