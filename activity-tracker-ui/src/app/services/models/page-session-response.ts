/* tslint:disable */
/* eslint-disable */
import { PageableObject } from '../models/pageable-object';
import { SessionResponse } from '../models/session-response';
import { SortObject } from '../models/sort-object';
export interface PageSessionResponse {
  content?: Array<SessionResponse>;
  empty?: boolean;
  first?: boolean;
  last?: boolean;
  number?: number;
  numberOfElements?: number;
  pageable?: PageableObject;
  size?: number;
  sort?: SortObject;
  totalElements?: number;
  totalPages?: number;
}
