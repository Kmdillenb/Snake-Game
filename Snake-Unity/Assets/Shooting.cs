using System.Collections;
using System.Collections.Generic;
using UnityEngine;


public class Shooting : MonoBehaviour
{
    
    // Start is called before the first frame update
    public Transform firePoint;
    public GameObject bulletPrefab;
    public GameObject snake;


    public Vector2 firedfrom = Vector2Int.right;  
    
    public Snake snakeGame;


    public float bulletForce = 20f;

    // Update is called once per frame
    void Update()
    {
       if(Input.GetKeyDown(KeyCode.E) && snakeGame.Length > 1){
            Shoot();
       } 
       if(Input.GetKeyDown(KeyCode.UpArrow)){
            firedfrom = Vector2Int.up;
       } 
       if(Input.GetKeyDown(KeyCode.DownArrow)){
            firedfrom = Vector2Int.down;
       } 
       if(Input.GetKeyDown(KeyCode.LeftArrow)){
            firedfrom = Vector2Int.left;
       } 
       if(Input.GetKeyDown(KeyCode.RightArrow)){
            firedfrom = Vector2Int.right;
       } 
    }

    void Shoot(){
        GameObject bullet = Instantiate(bulletPrefab, firePoint.position, firePoint.rotation);
        Rigidbody2D rb = bullet.GetComponent<Rigidbody2D>();
        rb.AddForce(firedfrom * bulletForce, ForceMode2D.Impulse);
    }
}
