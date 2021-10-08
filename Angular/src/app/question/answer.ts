import {Entry} from "./entry";
import {Comment} from "./comment";

export interface Answer extends Entry {
  comments: Comment[]
  solvesQuestion: boolean
}
