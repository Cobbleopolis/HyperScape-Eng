#version 330 core

uniform vec4 elementColor = vec4(0);

in vec4 pass_Color;
//in vec2 pass_TextureCoord;
//in vec3 pass_Normal;
out vec4 out_Color;

void main() {
    out_Color =  elementColor + pass_Color;
    if (out_Color.a == 0.0) {
        discard;
    }
}