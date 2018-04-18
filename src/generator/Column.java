/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generator;

/**
 *
 * @author a.kirillov
 */
public class Column 
{
private String Title;
public String getTitle()
{
    return Title;
}
public void setTitle(String value)
{
    this.Title=value;
}


private int W;
public int getWidth()
{
    return W;
}
public void setWidth(int value)
{
    this.W=value;
}
}
