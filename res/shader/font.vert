#version 330 core

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;

in vec3 in_Position;
in vec2 in_TextureCoord;
in vec3 in_Color;

out vec2 pass_TextureCoord;
out vec3 pass_Color;

void main() {
//    vec4 worldPos = viewMatrix * modelMatrix * vec4(in_Position, 1.0);
    gl_Position = projectionMatrix * viewMatrix * modelMatrix * vec4(in_Position, 1.0);
    pass_TextureCoord = in_TextureCoord;
    pass_Color = in_Color;
}