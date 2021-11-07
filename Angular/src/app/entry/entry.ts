import {User} from "../authentication/user";

export interface Entry {
  id: number;
  text: string;
  reputation: number;
  creationDate: Date;
  author: User;
}
