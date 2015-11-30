#version 330 core

uniform sampler2D texture_diffuse;
uniform vec4 chunkColor = vec4(1);
uniform int drawFog = 1;
//uniform vec3 viewDirection;

in vec2 pass_TextureCoord;
in float fogPct;
in vec3 pass_Normal;

out vec4 out_Color;

const vec4 fogColor = vec4(0.67058823529411764705882352941176, 0.8078431372549019607843137254902, 1.0, 1.0);

void main() {
    out_Color = chunkColor * texture(texture_diffuse, pass_TextureCoord);
    if (out_Color.a == 0.0) {
        discard;
    }

    if (drawFog == 1)
            if(fogPct > 0.0) {
                if(fogPct == 1.0)
                    discard;

                out_Color = out_Color * (1.0 - fogPct) + fogColor * fogPct;
            }
    //gl_FragColor = outColor;
}