using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class bullet : MonoBehaviour
{
    // Start is called before the first frame update

    public GameObject hitEffect;
    public GameObject Boss;

    private void OnTriggerEnter2D(Collider2D collider){
        // GameObject effect = Instantiate(hitEffect, transform.position, Quaternion.identity);
        Destroy(collider);
        }
}
