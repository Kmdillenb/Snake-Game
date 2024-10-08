using System.Collections;
using System.Collections.Generic;
using UnityEngine.Audio;
using UnityEngine;

[System.Serializable]
public class Sound {
    public AudioClip clip;

    public string name;

    [Range(0f,1f)]
    public float volume;
    [Range(.1f,.8f)]
    public float pitch;

    [HideInInspector]
    public AudioSource source;
    
}
