export interface CountByTagsResponse{
  count: number;
  tag: string;
}

export interface SubscriberCountByTagsResponse {
  data: CountByTagsResponse[];
}
