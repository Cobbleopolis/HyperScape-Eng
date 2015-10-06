#version 330 core

uniform sampler2D texture_diffuse;

in vec4 pass_Color;
in vec2 pass_TextureCoord;
in vec3 pass_Normal;

out vec4 out_Color;

void main() {
    out_Color = pass_Color * texture(texture_diffuse, pass_TextureCoord);
    if (out_Color.a == 0.0) {
        discard;
    }
}