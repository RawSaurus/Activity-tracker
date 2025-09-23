/* tslint:disable */
/* eslint-disable */
import { Profile } from '../models/profile';
import { Template } from '../models/template';
export interface Comment {
  commentId?: number;
  header?: string;
  likes?: number;
  postedAt?: string;
  profile?: Profile;
  template?: Template;
  text?: string;
  updatedAt?: string;
}
