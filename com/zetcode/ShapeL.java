package com.zetcode;

import java.util.Random;

public class ShapeL {
  
  protected enum TetrominoeL {
    NoShape, ZShape, SShape, LineShape,
    TShape, SquareShape, LShape, MirroredLShape
  }
  
  ;
  
  private TetrominoeL pieceShape;
  private int coords[][];
  private int[][][] coordsTable;
  
  
  ShapeL() {
    
    coords = new int[4][2];
    setShape(TetrominoeL.NoShape);
  }
  
  void setShape(TetrominoeL shape) {
    
    coordsTable = new int[][][]{
      {{0, 0}, {0, 0}, {0, 0}, {0, 0}},
      {{0, -1}, {0, 0}, {-1, 0}, {-1, 1}},
      {{0, -1}, {0, 0}, {1, 0}, {1, 1}},
      {{0, -1}, {0, 0}, {0, 1}, {0, 2}},
      {{-1, 0}, {0, 0}, {1, 0}, {0, 1}},
      {{0, 0}, {1, 0}, {0, 1}, {1, 1}},
      {{-1, -1}, {0, -1}, {0, 0}, {0, 1}},
      {{1, -1}, {0, -1}, {0, 0}, {0, 1}}
    };
    
    for (int i = 0; i < 4; i++) {
      
      for (int j = 0; j < 2; ++j) {
        
        coords[i][j] = coordsTable[shape.ordinal()][i][j];
      }
    }
    
    pieceShape = shape;
  }
  
  private void setX(int index, int x) {
    coords[index][0] = x;
  }
  
  private void setY(int index, int y) {
    coords[index][1] = y;
  }
  
  int x(int index) {
    return coords[index][0];
  }
  
  int y(int index) {
    return coords[index][1];
  }
  
  TetrominoeL getShape() {
    return pieceShape;
  }
  
  void setRandomShape() {
    
    Random r = new Random();
    int x = Math.abs(r.nextInt()) % 7 + 1;
    TetrominoeL[] values = TetrominoeL.values();
    setShape(values[x]);
  }
  
  public int minX() {
    
    int m = coords[0][0];
    
    for (int i = 0; i < 4; i++) {
      
      m = Math.min(m, coords[i][0]);
    }
    
    return m;
  }
  
  
  int minY() {
    
    int m = coords[0][1];
    
    for (int i = 0; i < 4; i++) {
      
      m = Math.min(m, coords[i][1]);
    }
    
    return m;
  }
  
  ShapeL rotateLeft() {
    
    if (pieceShape == TetrominoeL.SquareShape)
      return this;
    
    ShapeL result = new ShapeL();
    result.pieceShape = pieceShape;
    
    for (int i = 0; i < 4; ++i) {
      
      result.setX(i, y(i));
      result.setY(i, -x(i));
    }
    
    return result;
  }
  
  ShapeL rotateRight() {
    
    if (pieceShape == TetrominoeL.SquareShape)
      return this;
    
    ShapeL result = new ShapeL();
    result.pieceShape = pieceShape;
    
    for (int i = 0; i < 4; ++i) {
      
      result.setX(i, -y(i));
      result.setY(i, x(i));
    }
    
    return result;
  }
}
