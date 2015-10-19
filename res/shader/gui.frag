#version 330 core

uniform vec3 elementColor = vec3(0);
uniform int subtractColor = 0;

in vec4 pass_Color;
//in vec2 pass_TextureCoord;
//in vec3 pass_Normal;
out vec4 out_Color;

void main() {
    vec4 elemColor = vec4(elementColor, 0);
    if (subtractColor == 0)
        out_Color =  pass_Color + elemColor;
    else
        out_Color =  pass_Color - elemColor;

    if (out_Color.a == 0.0)
        discard;
}