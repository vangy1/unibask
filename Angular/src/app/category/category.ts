export interface Category {
  id: number;
  title: string;
  childrenCategories: Category[];
  questionCount: number;
  path: string[]
  followed: boolean;
}

