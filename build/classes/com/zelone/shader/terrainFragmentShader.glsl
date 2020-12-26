#version 400 core

in vec2 pass_textureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCameraVector;
in float visibility;

out vec4 out_Color;

uniform sampler2D blendSampler;
uniform sampler2D backgroundSampler;
uniform sampler2D rTextureSampler;
uniform sampler2D gTextureSampler;
uniform sampler2D bTextureSampler;

uniform vec3 lightColor;
uniform float shineDamper;
uniform float reflectivity;

uniform vec3 skyColor;

void main(void) {

    vec4 blendMapColor = texture(blendSampler, pass_textureCoords);

    float backTextureAmount = 1 - (blendMapColor.r + blendMapColor.g + blendMapColor.b);
    vec2 tiledCoords = pass_textureCoords * 40.0;
    vec4 backgroundTextureColor = texture(backgroundSampler, tiledCoords) * backTextureAmount;
    vec4 rTextureColor = texture(rTextureSampler, tiledCoords) * blendMapColor.r;
    vec4 gTextureColor = texture(gTextureSampler, tiledCoords) * blendMapColor.g;
    vec4 bTextureColor = texture(bTextureSampler, tiledCoords) * blendMapColor.b;

    vec4 totalColor =  backgroundTextureColor + rTextureColor + gTextureColor + bTextureColor;
    
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLightVector = normalize(toLightVector);
    vec3 unitCameraVector = normalize(toCameraVector);

    float nDot1 = dot(unitNormal, unitLightVector);
    float brightness = max(nDot1, 0.2);

    vec3 diffuse = brightness * lightColor;

    vec3 lightDirection = - unitCameraVector;
    vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
    float specularFactor = dot(reflectedLightDirection, unitCameraVector);
    specularFactor = max(specularFactor, 0.0);
    float dampedFactor = pow(specularFactor, shineDamper);
    vec3 finalSpecular = dampedFactor * reflectivity * lightColor;

    out_Color = vec4(diffuse, 1.0) * totalColor + vec4(finalSpecular, 1.0);
    out_Color = mix(vec4(skyColor, 1.0), out_Color, visibility);

}