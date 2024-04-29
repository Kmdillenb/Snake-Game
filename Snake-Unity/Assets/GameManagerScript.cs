using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class GameManagerScript : MonoBehaviour
{
    public Moving boss1;
    public GameObject gameOverUI;
    // Start is called before the first frame update
    void Start()
    {
        Cursor.visible = false;
        Cursor.lockState = CursorLockMode.Locked;
    }

    // Update is called once per frame
    void Update()
    {
        if(gameOverUI.activeInHierarchy){
            Cursor.visible = true;
            Cursor.lockState = CursorLockMode.None;
        }
    }

    public void gameOver(){
        FindObjectOfType<AudioManager>().Play("Ded");
        gameOverUI.SetActive(true);
    }

    public void restart(){
        boss1.GetComponent<Moving>().health = 3;
        FindObjectOfType<AudioManager>().Play("Restart");
        SceneManager.LoadScene(SceneManager.GetActiveScene().buildIndex);
    }
}
