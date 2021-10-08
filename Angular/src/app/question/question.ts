import {Entry} from "./entry";
import {Comment} from "./comment";
import {Answer} from "./answer";

export interface Question extends Entry {
  title: string;
  categoryName: string;
  answers: Answer[];
  comments: Comment[];
  solvedAnswerId: string;
}
