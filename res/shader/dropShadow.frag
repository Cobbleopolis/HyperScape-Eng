#version 330 core

uniform sampler2D texture_diffuse;

in vec2 pass_TextureCoord;

out vec4 out_Color;

void main() {
    vec4 color = texture(texture_diffuse, pass_TextureCoord);
    out_Color = vec4(color.xyz, color.a);
//    out_Color = vec4(1.0, 1.0, 1.0, 0.2);
    if (out_Color.a == 0.0) {
        discard;
    }
}