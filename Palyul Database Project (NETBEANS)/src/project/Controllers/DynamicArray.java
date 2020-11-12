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
public class DynamicArray<T> implements DynamicArrayInterface {
    public Object array[]; 
    public int count; 
    public int size; 
    
    public DynamicArray() {
        array = new Object[1];
        count = 0;
        size = 1;
    }

    @Override
    public void add(Object data) {
        if(count==size) {
            growSize();
        }
        array[count] = data;
        count++;
    }

    @Override
    public void growSize() {
        Object temp[] = null;
        if(count==size) {
            temp = new Object[size*2];
            
            for(int i=0; i<size; i++) {
                temp[i] = array[i];
            }
        }
        
        array=temp;
        size = size*2;
    }

    @Override
    public void shrinkSize() {
        Object temp[] = null;
        if(count > 0) {
            temp = new Object[count];
            for(int i=0; i<count; i++) {
                temp[i] = array[i];
            }
            size = count;
            array = temp;
        }
    }

    @Override
    public void addAt(int index, Object data) {
        if(count==size) {
            growSize();
        }
        
        for(int i=count-1; i>=index; i--) {
            array[i+1] = array[i];
        }
        
        array[index] = data;
        count++;
    }

    @Override
    public void remove() {
        if(count>0) {
            array[count-1] = 0;
            count--;
        }
    }

    @Override
    public void removeAt(int index) {
        if(count>0) {
            for(int i=index; i<count-1; i++) {
                array[i] = array[i+1];
            }
            array[count-1] = 0;
            count--;
        }
    }

    @Override
    public Object get(int index) {
        if(index<count) {
            return array[index];
        }
        else {
            return null;
        }
    }

    @Override
    public int size() {
        return count;
    }
    
}
