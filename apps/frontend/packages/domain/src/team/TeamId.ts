import { InvalidArgumentError, StringValueObject } from '@lyra/shared';

export default class TeamId extends StringValueObject {
	private constructor(value: string) {
		super(value);
		this.ensureIsValidUuid(value);
	}

	static random(): TeamId {
		return new TeamId(crypto.randomUUID());
	}

	public static create(value: string): TeamId {
		return new TeamId(value);
	}
	private ensureIsValidUuid(id: string): void {
		const regexExp =
			/^[0-9a-fA-F]{8}\b-[0-9a-fA-F]{4}\b-[0-9a-fA-F]{4}\b-[0-9a-fA-F]{4}\b-[0-9a-fA-F]{12}$/gi;

		const isValid = regexExp.test(id);
		if (!isValid) {
			throw new InvalidArgumentError(`<${this.constructor.name}> does not allow the value <${id}>`);
		}
	}
}
