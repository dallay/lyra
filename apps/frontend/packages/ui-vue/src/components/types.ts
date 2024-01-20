export type ExtractComponentProps<TComponent> = TComponent extends new () => {
	$props: infer P;
}
	? P
	: never;

export interface ColumnInfo {
	key: string;
	label: string;
}
