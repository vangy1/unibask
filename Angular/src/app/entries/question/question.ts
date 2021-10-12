import {Entry} from "../entry";
import {Comment} from "../comment/comment";
import {Answer} from "../answer/answer";

export interface Question extends Entry {
  title: string;
  categoryName: string;
  answers: Answer[];
  comments: Comment[];
  solvedAnswerId: string;
}
