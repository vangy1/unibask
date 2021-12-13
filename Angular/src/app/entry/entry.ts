import {User} from "../authentication/user";

export interface Entry {
  id: number;
  questionId: number
  text: string;
  reputation: number;
  myVote: number;
  creationDate: Date;
  author: User;
}
