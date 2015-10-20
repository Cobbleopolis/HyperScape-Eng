#version 330 core

#define PI 3.14159265359
uniform sampler2D texture_diffuse;

in vec2 pass_TextureCoord;

out vec4 out_Color;

void main() {
    out_Color = texture(texture_diffuse, pass_TextureCoord);
    if (out_Color.a == 0.0) {
        discard;
    }
}