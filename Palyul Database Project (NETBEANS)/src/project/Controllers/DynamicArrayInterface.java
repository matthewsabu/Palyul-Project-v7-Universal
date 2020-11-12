/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.Controllers;

/**
 *
 * @author Jan Go
 * @param <T>
 */
public interface DynamicArrayInterface<T> {
    public void add(T data);
    public void growSize();
    public void shrinkSize();
    public void addAt(int index, T data);
    public void remove();
    public void removeAt(int index);
    public T get(int index);
    public int size();
}
