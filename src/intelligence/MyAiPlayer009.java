package intelligence;

public class MyAiPlayer009 implements AiPlayerInterface {
	private Chess[][] chessBoard = new Chess[19][29];
	private Search search = new Search();
	private boolean[][] bound = new boolean[17][17];
	private String[][] map = new String[17][17];
	private int step = 0;
	private int imWho = 0;
	private int[] target = new int[2];
	private int stillHome = 0;
	private static final int NEGATIVE = 5;// �����Ȩ�ز���

	private static final int VOLUNTARY = 7;// ��������������Ȩ�ز���

	@Override
	public int[] jump(boolean[][] bound, String[][] map, int step) {
		if (step == 1) {
			int[] result = { 12, 2, 10, 4 };
			return result;
		} else if (step == 2) {
			int[] result = { 6, 14, 6, 12 };
			return result;
		} else if (step == 3) {
			int[] result = { 12, 0, 12, 4 };
			return result;
		} else if (step == 4) {
			int[] result = { 4, 16, 8, 12 };
			return result;
		} else if (step == 5) {
			int[] result = { 11, 2, 9, 4 };
			return result;
		} else if (step == 6) {
			int[] result = { 5, 14, 5, 12 };
			return result;
		} else if (step == 7) {
			int[] result = { 9, 3, 9, 5 };
			return result;
		} else if (step == 8) {
			int[] result = { 4, 13, 6, 11 };
			return result;
		} else if (step == 9) {
			int[] result = { 11, 1, 9, 3 };
			return result;
		} else if (step == 10) {
			int[] result = { 4, 15, 4, 13 };
			return result;
		} else {
			int[] result = new int[4];
			this.step = step;
			for (int i = 0; i <= 16; i++) {
				for (int j = 0; j <= 16; j++) {
					this.bound[i][j] = bound[i][j];
					this.map[i][j] = map[i][j];
				}
			}

			init();
			// convert coordinate
			convert();
			// get info of the board
			scan();
			// search every chess
			int[] bestChoice = { 0, 0, 0, 0 };
			if (imWho == 1) {
				int score = 0;
				// score=distace changed+ together + prevent negative
				// competition
				// piece return [0]score, which piece to move [1]row & [2]column
				for (int i = 0; i <= 18; i++) {
					for (int j = 0; j <= 28; j++) {
						if (chessBoard[i][j].getStatus() == 1) {
							if (pieceCompete(i, j)[0] >= score) {
								score = pieceCompete(i, j)[0];
								bestChoice[0] = i;
								bestChoice[1] = j;
								bestChoice[2] = pieceCompete(i, j)[1];
								bestChoice[3] = pieceCompete(i, j)[2];
							}
							clearIsChecked();
							clearStatus();

						}
					}
				}
			} else {
				// i m 2
				int score = 0;
				// score=distace changed+ together + prevent negative
				// competition
				// piece return [0]score, which piece to move [1]row & [2]column
				for (int i = 0; i <= 18; i++) {
					for (int j = 0; j <= 28; j++) {
						if (chessBoard[i][j].getStatus() == 2) {
							System.out.print("i&j:" + i + " " + j);
							if (pieceCompete(i, j)[0] >= score) {
								score = pieceCompete(i, j)[0];
								bestChoice[0] = i;
								bestChoice[1] = j;
								bestChoice[2] = pieceCompete(i, j)[1];
								bestChoice[3] = pieceCompete(i, j)[2];
							}
							clearIsChecked();
							clearStatus();
						}

					}
				}
			}
			System.out.print("\n\n\n\n");
			// ��ת��
			result[0] = reverseConvert(bestChoice[0], bestChoice[1])[0];
			result[1] = reverseConvert(bestChoice[0], bestChoice[1])[1];
			result[2] = reverseConvert(bestChoice[2], bestChoice[3])[0];
			result[3] = reverseConvert(bestChoice[2], bestChoice[3])[1];
			return result;
		}
	}

	public void scan() {
		target[0] = 0;
		target[1] = 0;
		if (imWho == 1) {
			// i m 1
			// enemy' home
			boolean setted = false;
			for (int i = 1; ((i <= 4) && (!setted)); i++) {
				int k = 0;
				for (int j = (15 - i); ((k < i) && (!setted)); k++) {
					if ((chessBoard[i][j].getStatus() == 1)) {
						chessBoard[i][j].setStatus(4);
						target[0] = i;
						target[1] = j;
					} else {
						target[0] = i;
						target[1] = j;
						setted = true;
					}
					j += 2;
				}
			}// System.out.print("target:"+target[0]+"  "+target[1]);
			
			
			
			// my home
			for (int i = 14; i <= 17; i++) {
				int k = 0;
				for (int j = (31 - i); k < (18 - i); k++) {
					if (chessBoard[i][j].getStatus() == 1) {
						stillHome++;
					}
					j -= 2;
				}
			}
		} else {
			// i m 2
			// enemy' home
			for (int i = 17; i >= 14; i--) {
				int k = 0;
				for (int j = (31 - i); k < (18 - i); k++) {
					if ((chessBoard[i][j].getStatus() == 2)) {
						target[0] = i;
						target[1] = j;
					} else {
						target[0] = i;
						target[1] = j;
						break;
					}
					j -= 2;
				}
			}
			// my home
			for (int i = 4; i >= 1; i--) {
				int k = 0;
				for (int j = (15 - i); k < i; k++) {
					if (chessBoard[i][j].getStatus() == 2) {
						stillHome++;
					}
					j += 2;
				}
			}
		}
	}

	public void init() {
		int row;
		int column;
		for (row = 0; row <= 18; row++) {
			for (column = 0; column <= 28; column++) {
				chessBoard[row][column] = new Chess();
				chessBoard[row][column].setIn(false);
				chessBoard[row][column].setStatus(-1);
				chessBoard[row][column].setChecked(false);
				chessBoard[row][column].setRowIndex(row);
				chessBoard[row][column].setColumnIndex(column);
			}
		}
		for (int i = 1; i <= 4; i++) {
			int k = 0;
			for (int j = (15 - i); k < i; k++) {
				chessBoard[i][j].setIn(true);
				chessBoard[i][j].setStatus(0);
				j += 2;
			}
		}
		for (row = 5; row < 9; row++) {
			for (column = row - 3; column <= 31 - row; column = column + 2) {
				chessBoard[row][column].setIn(true);
				chessBoard[row][column].setStatus(0);// 0 stands for blank
			}
		}
		for (row = 9; row < 14; row++) {
			for (column = 15 - row; column <= 13 + row; column = column + 2) {
				chessBoard[row][column].setIn(true);
				chessBoard[row][column].setStatus(0);
			}
		}

		for (int i = 14; i <= 17; i++) {
			int k = 0;
			for (int j = (31 - i); k < (18 - i); k++) {
				chessBoard[i][j].setIn(true);
				chessBoard[i][j].setStatus(0);// 2 stands for white
				j -= 2;
			}
		}
	}

	public void convert() {
		for (int i = 1; i <= 13; i++) {
			for (int j = 1; j <= i; j++) {
				if (map[3 + j][17 - i].equals("")) {
					chessBoard[i][15 - i + 2 * (j - 1)].setStatus(0);
				} else if (map[3 + j][17 - i].charAt(0) == '0') {
					chessBoard[i][15 - i + 2 * (j - 1)].setStatus(1);
				} else {
					chessBoard[i][15 - i + 2 * (j - 1)].setStatus(2);
				}
				if (bound[3 + j][17 - i]) {
					chessBoard[i][15 - i + 2 * (j - 1)].setIn(true);
				} else {
					chessBoard[i][15 - i + 2 * (j - 1)].setIn(false);
				}
			}
		}
		for (int i = 5; i <= 17; i++) {
			for (int j = 1; j <= (18 - i); j++) {
				if (map[13 - j][17 - i].equals("")) {
					chessBoard[i][31 - i - 2 * (j - 1)].setStatus(0);
				} else if (map[13 - j][17 - i].charAt(0) == '0') {
					chessBoard[i][31 - i - 2 * (j - 1)].setStatus(1);
				} else {
					chessBoard[i][31 - i - 2 * (j - 1)].setStatus(2);
				}
				if (bound[13 - j][17 - i]) {
					chessBoard[i][31 - i - 2 * (j - 1)].setIn(true);
				} else {
					chessBoard[i][31 - i - 2 * (j - 1)].setIn(false);
				}
			}
		}
	}

	public int[] reverseConvert(int row, int column) {
		int[] result = { 0, 0 };
		if (row <= 4 || ((row >= 9) && (row <= 13))) {
			// ���������ת��
			int i = row;
			int j = (column - 15 + i) / 2 + 1;
			result[0] = 3 + j;
			result[1] = 17 - i;
		} else {
			int i = row;
			int j = (column - 31 + i) / (-2) + 1;
			result[0] = 13 - j;
			result[1] = 17 - i;
		}
		return result;
	}

	public int[] pieceCompete(int row, int column) {
		// piece return [0]score, which piece to move [1]row & [2]column
		// ���۱�׼������ı���****+��ֹ��***+��Ⱥ��***
		// ÿ������ѡ��һ��������ߵ��߷���Ȼ���ó�ȥ�ͱ�����ӵ���߷ֱȽ�
		int[] result = new int[3];
		result[0] = 0;
		// ԭ�������
		double distance = getDistance(row, column);
		// ԭ�����������
		// ��Ⱥ�ȷ���
		int chessAround = togetherAna(row, column);
		// Դ���Ӻ�Ⱥ�ȷ�������
		// ��ֹ�����stillHomeΪ��Ȼ�ڼҵ�������stepΪ�ڼ���
		search.mainSearchPack(row, column);
		System.out.println("step:" + step);
		System.out.println("target:" + target[0] + "  " + target[1]);
		/*
		 * for(int i =0;i<=18;i++){for(int j =0;j<=28;j++){
		 * if(chessBoard[i][j].getStatus()==-1){System.out.print("  ");}
		 * if(chessBoard[i][j].getStatus()==0){System.out.print("**");}
		 * if(chessBoard[i][j].getStatus()==1){System.out.print("[[");}
		 * if(chessBoard[i][j].getStatus()==2){System.out.print("]]");}
		 * if(chessBoard[i][j].getStatus()==3){System.out.print("..");}
		 * }System.out.print("\n");}
		 */
		// ��ֹ�����
		int nega = 0;
		if (((imWho == 1) && (row >= 14)) || ((imWho == 2) && (row <= 4))) {
			System.out.println("�����ִ���ˣ�" + "����" + step + " ����" + row + " ����"
					+ column);
			if (step >= 50) {
				nega = NEGATIVE * 10;
			} else if (step >= 44) {
				nega = NEGATIVE * 10;
			} else if (step >= 30) {
				nega = NEGATIVE * 10;
			} else if (step <= 18) {
				nega = VOLUNTARY;
			}
		}
		System.out.println("nega:" + nega);

		// ���ÿ���������ӵķ���
		for (int i = 0; i <= 18; i++) {
			for (int j = 0; j <= 28; j++) {
				if (chessBoard[i][j].getStatus() == 3) {
					int[] temp = new int[3];
					temp[0] = nega;
					temp[1] = i;
					temp[2] = j;
					// ////////////////////////////////////////para to be better
					// ����ı���
					temp[0] = temp[0]+ (int) (18 - getDistance(temp[1], temp[2]));
					// ////////////////////////////////////////para to be better
					// ��Ⱥ��
					int togetherNum = togetherAna(i, j)*2;
					temp[0] = temp[0] + togetherNum;
					// ////////////////////////////////////////para to be better
					System.out.println("now����" + temp[0] + "top score"
							+ result[0] + " ����" + row + " ����" + column);
					if (temp[0] > result[0]) {
						result[0] = temp[0];
						result[1] = temp[1];
						result[2] = temp[2];
					}
				}
			}
		}
		return result;
	}

	public void clearIsChecked() {
		for (int row = 0; row <= 18; row++) {
			for (int column = 0; column <= 28; column++) {
				chessBoard[row][column].setChecked(false);
			}
		}
	}

	public void clearStatus() {
		for (int row = 0; row <= 18; row++) {
			for (int column = 0; column <= 28; column++) {
				if (chessBoard[row][column].getStatus() == 3) {
					chessBoard[row][column].setStatus(0);
				}
			}
		}
	}

	public double getDistance(int row, int column) {
		double distance = 0;
		distance = Math.sqrt(((row - target[0]) * 0.5 * Math.sqrt(3))
				* ((row - target[0]) * 0.5 * Math.sqrt(3))
				+ ((column - target[1]) * 0.5) * ((column - target[1]) * 0.5));
		return distance;
	}

	public int togetherAna(int row, int column) {
		int chessAround = 0;
		if (chessBoard[row][column + 2].getStatus() != 0
				|| chessBoard[row][column + 2].getStatus() != -1) {
			chessAround++;
		}
		if (chessBoard[row + 1][column + 1].getStatus() == 1
				|| chessBoard[row + 1][column + 1].getStatus() == 2) {
			chessAround++;
		}
		if (chessBoard[row + 1][column - 1].getStatus() == 1
				|| chessBoard[row + 1][column - 1].getStatus() == 2) {
			chessAround++;
		}
		if (chessBoard[row - 1][column + 1].getStatus() == 1
				| chessBoard[row - 1][column + 1].getStatus() == 2) {
			chessAround++;
		}
		if (chessBoard[row - 1][column - 1].getStatus() == 1
				|| chessBoard[row - 1][column - 1].getStatus() == 2) {
			chessAround++;
		}
		if (chessBoard[row][column - 2].getStatus() == 1
				|| chessBoard[row - 1][column - 1].getStatus() == 2) {
			chessAround++;
		}
		return chessAround;
	}

	class Chess {
		private boolean isIn = false;// out=-1
		private boolean isChecked = false;
		// private boolean isKept=false;//kept=4
		private int status = -1;

		private int rowIndex = 1;
		private int columnIndex = 1;

		Chess() {
			setChecked(false);
			setColumnIndex(1);
			setRowIndex(1);
			setIn(false);
			setStatus(-1);
		}

		public void setIn(boolean isIn) {
			this.isIn = isIn;
		}

		public boolean getIn() {
			return this.isIn;
		}

		public void setChecked(boolean isChecked) {
			this.isChecked = isChecked;
		}

		public boolean getChecked() {
			return this.isChecked;
		}

		public void setStatus(int number) {
			this.status = number;
		}

		public int getStatus() {
			return this.status;
		}

		public int getRowIndex() {
			return rowIndex;
		}

		public int getColumnIndex() {
			return columnIndex;
		}

		public void setRowIndex(int rowIndex) {
			this.rowIndex = rowIndex;
		}

		public void setColumnIndex(int columnIndex) {
			this.columnIndex = columnIndex;
		}
	}

	class Search {
		private int rowSel = 0;
		private int columnSel = 0;

		public void mainSearchPack(int rowSel, int columnSel) {
			this.rowSel = rowSel;
			this.columnSel = columnSel;

			// start searching
			searchPack(rowSel, columnSel);
			closeMove();
			// search ends
		}

		/**
		 * ����һ����֯������������㷨�����������������������search()������
		 */
		public void searchPack(int row, int column) {
			if (imWho == 1) {
				search(row, column, -1, -1);// right top
				search(row, column, -1, +1);// left top
				
			} else {
				search(row, column, +1, -1);// right bottom
				search(row, column, +1, 1);// left bottom

			}/*
			 * search(row, column, 0, -2);// left search(row, column, 0, +2);//
			 * right search(row, column, 1, 1);// right bottom search(row,
			 * column, 1, -1);// right top search(row, column, -1, 1);// left
			 * bottom search(row, column, -1, -1);// left top
			 */
		}

		/**
		 * ����������ڴ��������Ų����
		 */
		public void closeMove() {
			int row = rowSel;
			int column = columnSel;
			if (imWho == 1) {
				if (chessBoard[row - 1][column + 1].getStatus() == 0) {
					chessBoard[row - 1][column + 1].setStatus(3);
				}
				if (chessBoard[row - 1][column - 1].getStatus() == 0) {
					chessBoard[row - 1][column - 1].setStatus(3);
				}
			} else {
				if (chessBoard[row + 1][column - 1].getStatus() == 0) {
					chessBoard[row + 1][column - 1].setStatus(3);
				}
				if (chessBoard[row + 1][column + 1].getStatus() == 0) {
					chessBoard[row + 1][column + 1].setStatus(3);
				}
			}
			/*
			 * if (chessBoard[row][column - 2].getStatus() == 0) {
			 * chessBoard[row][column - 2].setStatus(3); } if
			 * (chessBoard[row][column + 2].getStatus() == 0) {
			 * chessBoard[row][column + 2].setStatus(3); } if (chessBoard[row +
			 * 1][column - 1].getStatus() == 0) { chessBoard[row + 1][column -
			 * 1].setStatus(3); } if (chessBoard[row + 1][column +
			 * 1].getStatus() == 0) { chessBoard[row + 1][column +
			 * 1].setStatus(3); } if (chessBoard[row - 1][column +
			 * 1].getStatus() == 0) { chessBoard[row - 1][column +
			 * 1].setStatus(3); } if (chessBoard[row - 1][column -
			 * 1].getStatus() == 0) { chessBoard[row - 1][column -
			 * 1].setStatus(3); }
			 */
		}

		/**
		 * �����ײ��ʵ�ʵ������㷨,����������
		 * 
		 * @author Igloo
		 */
		public void search(int row, int column, int r, int c) {
			boolean moreJump = false;
			chessBoard[row][column].setChecked(true);
			if (chessBoard[row + r][column + c].getIn()) {
				if (chessBoard[row + 2 * r][column + 2 * c].getIn()) {
					if (chessBoard[row + r][column + c].getStatus() == 1
							|| chessBoard[row + r][column + c].getStatus() == 2) {
						if (chessBoard[row + 2 * r][column + 2 * c].getStatus() == 0) {
							moreJump = true;
							chessBoard[row + 2 * r][column + 2 * c]
									.setStatus(3);
						}
					}

					if (moreJump
							&& (chessBoard[row + r * 2][column + c * 2]
									.getChecked() == false)) {
						searchPack((row + r * 2), (column + c * 2));
					}
				}
			}
		}
	}

	@Override
	public void setWho(int who) {
		if (who == 0) {
			imWho = 1;
		} else {
			imWho = 2;
		}
	}
}
