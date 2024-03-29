#version 330 core

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;

in vec3 in_Position;
in vec2 in_TextureCoord;
in vec3 in_Normal;

out vec4 pass_Color;
out vec2 pass_TextureCoord;
out float fogPct;
out vec3 pass_Normal;


const float begin = 48.0;
const float end = 64.0;
const float range = end - begin;
void main() {
    vec4 worldPos = viewMatrix * modelMatrix * vec4(in_Position, 1.0);
    gl_Position = projectionMatrix * worldPos;

    pass_Color = vec4(1);
    pass_Normal = in_Normal;

    pass_TextureCoord = in_TextureCoord;
}