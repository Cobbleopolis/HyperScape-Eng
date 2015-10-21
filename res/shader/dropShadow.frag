#version 330 core

uniform sampler2D texture_diffuse;
uniform float in_zIndex;

in vec2 pass_TextureCoord;

out vec4 out_Color;

void main() {
    out_Color = texture(texture_diffuse, pass_TextureCoord) * vec4(1.0, 1.0, 1.0, 0.2);
    if (out_Color.a == 0.0)
        discard;
}