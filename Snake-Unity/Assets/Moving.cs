using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Moving : MonoBehaviour
{
    private Rigidbody2D rb;
    private Transform currentPoint;

    public int y_Direction = 1;

    public int x_Direction = 1;
    public int health = 5;


    public float speed;
    // Start is called before the first frame update
    void Start()
    {
        health = 5;
        speed = 10;
        rb = GetComponent<Rigidbody2D>();
        Vector2 moving = new Vector2(speed, 0);
    }

    // Update is called once per frame
    void Update()
    {
        rb.velocity = new Vector2(speed * x_Direction, speed * y_Direction);
        // if(health <= 0){
        //     Debug.Log("Worked");
        //     Destroy(this.gameObject);
        // }
    }

    private void OnTriggerEnter2D(Collider2D collider){
        

        if(collider.gameObject.CompareTag("Food")){
            Physics2D.IgnoreLayerCollision(7, 6);
            
        }
        else if(collider.gameObject.CompareTag("Bullet")){
            health -= 1;
            if(health <= 0){
                Destroy(collider);
                Destroy(gameObject);
            }
        }
        if(collider.name == "BottomWall"){
            y_Direction = 1;
        }
        else if(collider.name == "RightWall"){
            x_Direction = -1;
        }
        else if(collider.name == "TopWall"){
            
            y_Direction = -1;
        }
        else if(collider.name == "LeftWall"){
            x_Direction = 1;
        }
        else if(collider.name == "Boss1(Clone)"){
            x_Direction *= -1;
            y_Direction *= -1;
        }
    }

}
