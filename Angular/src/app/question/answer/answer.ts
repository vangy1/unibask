import {Entry} from "../../entry/entry";
import {Comment} from "../comment/comment";

export interface Answer extends Entry {
  comments: Comment[]
  solvesQuestion: boolean
}
