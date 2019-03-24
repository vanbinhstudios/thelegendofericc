#ifdef GL_ES
    precision mediump float;
#endif
            
varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;

void main() {
    vec4 c = texture2D(u_texture, v_texCoords);
    float brightness = v_color.b;
    float saturation = v_color.g;
    float grey = dot(c.rgb, vec3(.1, .2, 0.71));
    c = mix(vec4(grey, grey, grey, c.a), c, saturation);
    c *= vec4(brightness, brightness, brightness, 1);

    gl_FragColor = c;
};
