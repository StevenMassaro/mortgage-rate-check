# Mortgage Interest Rate Checker

![ci](https://github.com/StevenMassaro/mortgage-rate-check/actions/workflows/build.yml/badge.svg)
[![Docker](https://badgen.net/badge/icon/docker?icon=docker&label)](https://hub.docker.com/r/stevenmassaro/mortgage-rate-check)

Automatically check mortgage interest rates and notify through Telegram.

### Motivation

I want to stay updated about interest rates, so I know whether to refinance my mortgage or not.

## Usage

### Environment variables

| Name               | Required | Default value | Example value                                                 |
|--------------------|----------|---------------|---------------------------------------------------------------|
| `BOT_TOKEN`        | Yes      |               | `bot123456:ABC-DEF1234ghIkl-zyx57W2v1u123ew11`                |
| `CHAT_ID`          | Yes      |               | `123456789`                                                   |
| `HEALTHCHECKS_URL` | No       |               | `https://hc-ping.com/00000000-0000-0000-0000-000000000000`    |
| `DELAY`            | No       | `1d`          | `7d` (or any other permissible value for the `sleep` command) |

`BOT_TOKEN` and `CHAT_ID` are obtained after [creating your own Telegram bot](https://core.telegram.org/bots/tutorial).

### Docker

```dockerfile
  mortgage-rate-check:
    image: stevenmassaro/mortgage-rate-check
    container_name: mortgage-rate-check
    cap_add:
      # SYS_ADMIN permissions are necessary for Selenium to operate correctly.
      - SYS_ADMIN
    environment:
      BOT_TOKEN: bot123456:ABC-DEF1234ghIkl-zyx57W2v1u123ew11
      CHAT_ID: 123456789
      HEALTHCHECKS_URL: https://hc-ping.com/00000000-0000-0000-0000-000000000000
```