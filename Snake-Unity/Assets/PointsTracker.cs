using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class PointsTracker : MonoBehaviour
{
    public static PointsTracker instance;

    public Text scoreText;
    public Text highscoreText;

    public Snake SnakeGame;

    int score = 0;
    static int highscore = 0;

    private void Awake(){
        instance = this;
    }

    private void Start(){
        score = 0;
        scoreText.text = score.ToString() + " POINTS";
        highscoreText.text = "HIGHSCORE: " + highscore.ToString();
    }
    private void Update()
    {
        score = SnakeGame.GetComponent<Snake>().score;
        UpdateScore();
    }
     void UpdateScore(){
        scoreText.text = score.ToString() + " POINTS";
        if(highscore < score){
            highscore = score;
            highscoreText.text = "HIGHSCORE: " + highscore.ToString();
        }
     }
}
