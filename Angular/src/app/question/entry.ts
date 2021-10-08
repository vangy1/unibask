import {Author} from "./author";

export interface Entry {
  id: string;
  text: string;
  reputation: string;
  creationDate: string;
  author: Author;
}
