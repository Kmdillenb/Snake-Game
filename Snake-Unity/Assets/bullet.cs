using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class bullet : MonoBehaviour
{
    // Start is called before the first frame update

    public GameObject hitEffect;
    public GameObject Boss;

    void OnCollisionEnter2D(Collision2D collision){
        // GameObject effect = Instantiate(hitEffect, transform.position, Quaternion.identity);
        Destroy(this.gameObject);
        }
}
