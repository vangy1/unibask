import {Author} from "./author/author";

export interface Entry {
  id: string;
  text: string;
  reputation: string;
  creationDate: string;
  author: Author;
}
