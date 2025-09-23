/* tslint:disable */
/* eslint-disable */
import { Links } from '../models/links';
export interface EntityModelEventResponse {
  '_links'?: Links;
  end?: string;
  linkId?: number;
  name?: string;
  start?: string;
  type?: 'SESSION' | 'ACHIEVEMENT_DONE' | 'PLAN';
}
