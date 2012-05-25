uniform vec3 toonColor;
uniform sampler2D jvr_Texture0;

varying vec2 texture_coordinate;
varying vec3 normalV;
varying vec3 lightDirV;
varying vec3 eyeDirV;

void main (void)
{
  vec4 color = texture2D(jvr_Texture0, texture_coordinate);
  //vec4 color = vec4(1,0,0,1);
  vec3 N = normalize(normalV);
  vec3 L = normalize(lightDirV);
  vec3 E = normalize(eyeDirV);
  
  /* diffuse intensity */
  float intensity = dot(L, N);

  color = intensity * color;
  
  /* specular highlight */
  if (intensity > 0.0) {
    vec3 R = reflect(-L, N);
    float specular = max(dot(R, E), 0.0);
   // if (specular > 0.99)
    //  color = jvr_LightSource_Specular.rgb;
  }
  
  gl_FragColor = color;
  gl_FragColor.a = texture2D(jvr_Texture0, texture_coordinate).a;
}
