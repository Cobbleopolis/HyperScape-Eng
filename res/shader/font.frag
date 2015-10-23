#version 330 core

uniform sampler2D texture_diffuse;
uniform vec4 textColor = vec4(1.0);

in vec2 pass_TextureCoord;

out vec4 out_Color;

void main() {
    out_Color = textColor * texture(texture_diffuse, pass_TextureCoord);
    if (out_Color.a < 1.0)
        discard;
//    out_Color = vec4(1.0, 1.0, 1.0, 1.0);
}